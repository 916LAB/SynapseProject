const socket = new SockJS('http://localhost:8080/gs-guide-websocket');

let stompClient = null;
socket.addEventListener('open', () => {
    stompClient = Stomp.over(socket);

    function displayMessage(message) {
        console.log(message);
    }

    stompClient.connect({}, (frame) => {
        // 경로 이후에 재설정 필요 roomid 로 재설정해야함
        stompClient.subscribe('/pub/messages', (message) => {
            displayMessage(JSON.parse(message.body));
        });
    });
});
socket.onopen = function (event) {
    console.log('WebSocket 연결이 성공적으로 열림:', event);
};

socket.onerror = function (error) {
    console.log('WebSocket 연결 중 오류 발생:', error);
};
document.addEventListener('DOMContentLoaded',()=>{
    const chatInput = document.getElementById('chat-input');
    const sendMessageButton = document.getElementById('sendMessageButton');

    sendMessageButton.addEventListener('click', () => {
        sendMessage(chatInput.value, chatInput);
    });
})

function sendMessage(message, chatInput) {
    // 연결이되어있고 메시지가 비어있는게 아니라면 /app/chat 주소로 메시지 데이터를 JSON 형식으로 전송
    if (stompClient && message) {
        stompClient.send("/app/chat", {}, JSON.stringify({ 'content': message, 'sender' : "asd", 'reciver' : "조봉균" , 'roomid' : "skr"}));

        chatInput.value = "";
    }
}
