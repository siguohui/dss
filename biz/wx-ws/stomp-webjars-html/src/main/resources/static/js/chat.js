var stompClient = null;

//建立一个 WebSocket 连接，在建立 WebSocket 连接时，用户必须先输入用户名，然后才能建立连接
function connect() {
    //使用 SockJS 建立连接，然后创建一个 STOMP 实例发起连接请求 在连接成功的回调方法中，
    // 首先调用 setConnected(true);方法进行页面的设置，然后调用 STOMP 中的 subscribe 方法订阅服务端发送回来的消息，并将服务端发送来的消息展示出来（使用 showGreeting 方法）
    var socket = new SockJS('/chat');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        stompClient.subscribe('/user/queue/chat', function (chat) {
            showGreeting(JSON.parse(chat.body));
        });
    })
}

function sendMsg() {
    stompClient.send("/app/chat", {},
        JSON.stringify({
            'content': $("#content").val(), 'to': $("#to").val()
        }));
}

function showGreeting(message) {
    console.log("message--->",message)
    $("#chatsContent").append("<div>" + message.from + ":" + message.content + "</div>");
}

$(function () {
    connect();
    $("#send").click(function () {
        sendMsg();
    });
})
