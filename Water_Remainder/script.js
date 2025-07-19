let intervalId = null;

function notify() {
  if (Notification.permission === 'granted') {
    new Notification("💧 Time to drink water!");
  } else {
    Notification.requestPermission().then(permission => {
      if (permission === 'granted') {
        new Notification("💧 Time to drink water!");
      }
    });
  }
}

function startReminder() {
  if (intervalId) clearInterval(intervalId);

  const interval = parseInt(document.getElementById("interval").value);
  notify();
  alert(`Reminder started! You'll get notified every ${interval} minute(s).\n⚠️ Keep this tab open for notifications to continue.`);

  intervalId = setInterval(() => {
    notify();
  }, interval * 60 * 1000);
}

function stopReminder() {
  clearInterval(intervalId);
  intervalId = null;
}
