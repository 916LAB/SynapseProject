let socket = null;
let stompClient = null;
let roomId = null;

document.addEventListener('DOMContentLoaded', () => {
    socket = new SockJS('http://localhost:8080/gs-guide-websocket');
    roomId = document.getElementById("hid-roomid").value;

    console.log('현재 roomId:', roomId);

    stompClient = Stomp.over(socket);
    // Message 출력
    function displayMessage(message) {
        console.log(message);
    }

    // stomp클라이언트 연결, 구독할 페이지 설정
    stompClient.connect({}, (frame) => {
        stompClient.subscribe('/pub/messages', (message) => {
            displayMessage(JSON.parse(message.body));
        });

        // 2초 후에 실행될 코드
        setTimeout(() => {
            const chatListItems = document.querySelectorAll('.chat-list li');

            console.log('Found', chatListItems.length, 'chat list items');

            chatListItems.forEach(item => {
                console.log('Chat list item text:', item.textContent);

                item.addEventListener('dblclick', (event) => {
                    document.querySelector('.chat-partner-name').textContent = event.target.textContent;
                    // window.location.href = '/{}';
                });
            });
        }, 2000);  // 2000ms = 2s

    });


    const chatInput = document.getElementById('chat-input');
    const sendMessageButton = document.getElementById('sendMessageButton');

    // 메시지 전송 버튼 이벤트
    sendMessageButton.addEventListener('click', () => {
        sendMessage(chatInput.value, chatInput, roomId);
    });
});

// 메시지 가공
function sendMessage(message, chatInput, roomId) {
    console.log('sendMessage:', message);

    if (stompClient && message) {
        stompClient.send("/app/chat", {}, JSON.stringify({ 'content': message, 'sender' : "asd", 'reciver' : "조봉균" , 'roomid' : roomId}));
        chatInput.value = "";
    }
}
