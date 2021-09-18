importScripts("https://www.gstatic.com/firebasejs/8.6.1/firebase-app.js");
importScripts("https://www.gstatic.com/firebasejs/8.6.1/firebase-messaging.js");

firebase.initializeApp({
  apiKey: "AIzaSyBp5H4h4BUsbYuyCdlMKhlArTjHCXD1phU",
  authDomain: "adventureit.firebaseapp.com",
  databaseURL: "https://adventureit.firebaseio.com",
  projectId: "adventureit",
  storageBucket: "adventureit.appspot.com",
  messagingSenderId: "747889907400",
  appId: "1:747889907400:android:cbfd91781ec27c67daf7fd",
  measurementId: "G-...",
});
// Necessary to receive background messages:
const messaging = firebase.messaging();

// Optional:
messaging.onBackgroundMessage((m) => {
  console.log("onBackgroundMessage", m);
});
