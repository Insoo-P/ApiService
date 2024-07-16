<template>
  <div>
    <h2>Component 5</h2>
    <p>This is content for Component 1.</p>
    <div class="calendar-container">
      <div ref="calendarRef" class="calendar"></div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { Calendar } from '@fullcalendar/core';
import dayGridPlugin from '@fullcalendar/daygrid';

const calendarRef = ref(null);

onMounted(() => {
  const calendar = new Calendar(calendarRef.value, {
    plugins: [ dayGridPlugin ],
    initialView: 'dayGridMonth',
    events: [
      { title: 'Event 1', date: '2024-07-16' },
      { title: 'Event 2', date: '2024-07-17' }
    ]
  });

  calendar.render();
});
</script>

<style>
.calendar-container {
  width: 80%; /* 원하는 너비 설정 */
  margin: 0 auto; /* 가운데 정렬을 위해 */
}

.calendar {
  width: 100%; /* 부모 요소의 너비를 100%로 설정 */
  height: 600px; /* 원하는 높이 설정 */
}
</style>
