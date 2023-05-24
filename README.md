# Websocket Example

![ui](./ui-example.gif)

## 구성

- ui 에서 각 탭당 websocket 1개를 이용해서 통신하는 예제
- greeting
  - websocket 으로 메세지를 보내면 `/topic/greeting` 을 구독한 웹소켓에 브로드캐스팅 하는 예제
- query
  - rest api 로 쿼리를 보내고, `/topic/query` 을 구독한 웹소켓으로 결과를 받는 예제
- multi-query
  - query 탭을 여러개로 구성

## 언어

- server: java 11 (spring-boot)
- web: vue.js
