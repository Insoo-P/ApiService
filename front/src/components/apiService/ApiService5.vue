<template>
  <div>
    <h2>Component 5</h2>
    <p>This is content for Component 1.</p>
    <div class="calendar-container">
      <div ref="calendarRef" class="calendar"></div>
    </div>
    <h2>event 생성</h2>
    <div>
      <label>title:</label>
      <input v-model="eventTitle" type="text">
      <label>date:</label>
      <input v-model="eventDate" type="date">
      <button @click="eventAdd">event 생성</button>
    </div>
    <h2>event 목록</h2>
    <div id="external-events">
      <ul v-if="eventList.length > 0">
        <li class="fc-event" v-for="event in eventList" :key="event">
          {{ event }}
        </li>
      </ul>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { Calendar } from '@fullcalendar/core';
import dayGridPlugin from '@fullcalendar/daygrid';
import interactionPlugin from '@fullcalendar/interaction';

const calendar = ref(null);
const calendarRef = ref(null);
const eventTitle =ref('');
const eventDate =ref('');
const eventList = ref([]);

function eventAdd(){
  console.log(calendarRef.value)
  console.log(calendar.value)
  calendar.value.addEvent({title: eventTitle.value, date: eventDate.value})
  eventList.value = calendar.value.getEvents()
}

onMounted(() => {
  calendar.value = new Calendar(calendarRef.value, {
    plugins: [ dayGridPlugin, interactionPlugin ],
    initialView: 'dayGridMonth',
    headerToolbar: {
      left: 'today, prev,next',
      center: 'title',
      right: 'dayGridYear,dayGridMonth,dayGridWeek,dayGridDay,dayGridFourWeek' // user can switch between the two
    },
    // 커스텀 
    views: {
      dayGridFourWeek: {
        type: 'dayGridWeek',
        duration: { weeks: 2 }
      }
    },
    events: [
      { title: 'Event 1', date: '2024-07-16' },
      { title: 'Event 2', date: '2024-07-17', description: 'This is a sample event. This is a sample event. This is a sample event', durationEditable: true }
    ],
    eventDrop: function(info) {// 이벤트가 드래그 앤 드롭으로 옮겨졌을 때 호출되는 함수
        info.event.setStart(info.event.date)
        eventList.value = calendar.value.getEvents()
    },
    eventClick: function(info){
      alert(info.event.start, info.event.extendedProps.description)
    },
    droppable: true, // 외부 드래그 앤 드롭을 허용
    editable: true, // 이벤트가 드래그 앤 드롭으로 수정 가능하게 true
  });
  
  calendar.value.render();
  eventList.value = calendar.value.getEvents()

});
</script>

<style>
.calendar-container {
  width: 100%; /* 원하는 너비 설정 */
  margin: 0 auto; /* 가운데 정렬을 위해 */
}

.calendar {
  width: 100%; /* 부모 요소의 너비를 100%로 설정 */
  height: 600px; /* 원하는 높이 설정 */
}
</style>
