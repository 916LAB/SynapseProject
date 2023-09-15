let socket = null;
let stompClient = null;
let roomId = null;
var sessionid = null;
var partnerName = null;

document.addEventListener('DOMContentLoaded', () => {
    sessionid = document.body.dataset.sessionId;
    console.log('현재 id : ', sessionid);

    if (!sessionid) {
        console.error('세션 ID가 없거나 유효하지 않습니다.');
        return;
    }

    function connectAndSubscribe(roomId) {
        if (socket) {
            socket.close();
        }

        socket = new SockJS('http://localhost:8080/gs-guide-websocket');
        stompClient = Stomp.over(socket);

        stompClient.connect({}, (frame) => {
            stompClient.subscribe(`/pub/${roomId}`, (message) => {
                displayMessage(JSON.parse(message.body));
            });
        });
        console.log(roomId);
    }

    roomId = document.getElementById("hid-roomid").value;
    console.log('현재 roomId:', roomId);

    connectAndSubscribe(roomId);

// Message 출력
// Message 출력
    function displayMessage(message) {
        const chatHistoryElement = document.querySelector('.chat-history');

        const messageElement= document.createElement("div");
        messageElement.classList.add("message");

        if(message.sender == sessionid){
            messageElement.classList.add("my-message");
        }else{
            messageElement.classList.add("other-message");
        }

        const contentElement= document.createElement("div");
        contentElement.classList.add("message-content");

        const p1= document.createElement("p");

        p1.textContent = message.content;

        contentElement.appendChild(p1);

        messageElement.appendChild(contentElement);

        chatHistoryElement.append(messageElement);
    }

    setTimeout(() => {

        const chatListItems = document.querySelectorAll('.chat-list li');
        console.log('Found', chatListItems.length, 'chat list items');

        chatListItems.forEach(item => {

            item.addEventListener('dblclick', (event) => {

                partnerName = event.target.textContent;
                document.querySelector('.chat-partner-name').textContent = partnerName;

                fetch(`/api/chat_room?partner=${encodeURIComponent(partnerName)}`)
                    .then(response => response.text())  // text()로 변경
                    .then(newroomId => {  // roomId로 변경
                        console.log('Updating roomId from', roomId, 'to', newroomId);
                        roomId = newroomId;
                        connectAndSubscribe(roomId);  // roomId 사용

                        fetch(`/api/chat_history?partner=${encodeURIComponent(partnerName)}&room=${roomId}`)
                            .then(response => response.json())
                            .then(messages => {
                                const chatHistoryElement = document.querySelector('.chat-history');

                                // 기존의 모든 메시지 삭제
                                chatHistoryElement.innerHTML ='';
                                messages.forEach(message => {

                                    const messageElement= document.createElement("div");
                                    messageElement.classList.add("message");

                                    if(message.sender == sessionid){
                                        messageElement.classList.add("my-message");
                                    }else{
                                        messageElement.classList.add("other-message");
                                    }

                                    const contentElement= document.createElement("div");
                                    contentElement.classList.add("message-content");

                                    const p1= document.createElement("p");

                                    p1.textContent=message.content;

                                    contentElement.appendChild(p1);

                                    messageElement.appendChild(contentElement);

                                    chatHistoryElement.append(messageElement);
                                });
                            })
                            .catch(error =>{
                                console.error('Error fetching chat history:', error);
                            });
                    })
                    .catch(error => console.error('Error fetching chat room:', error));
            });
        });

    }, 2000);

    const chatInput = document.getElementById('chat-input');
    const sendMessageButton = document.getElementById('sendMessageButton');

    sendMessageButton.addEventListener('click', () => {
        if (partnerName === null) {
            console.error('상대방을 선택해주세요.');
            return;
        }
        sendMessage(chatInput.value, chatInput);
    });

    function sendMessage(message, chatInput) {
        console.log('sendMessage:', message);

        if (stompClient && message) {
            stompClient.send(`/app/${roomId}`, {}, JSON.stringify({ 'content': message, 'sender' : sessionid, 'reciver' : partnerName , 'roomid' : roomId}));
            chatInput.value = "";
        }
    }
});
