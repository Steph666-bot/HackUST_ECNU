<template>
  <div class="voterList">
    <li v-for="item in voters">
      <div class="voter">
        <div class="icon">{{ item.name[0] }}</div>
        {{ item.name }}
        <div class="voted">voted</div>
        {{ item.option }}
        <div class="timestamp">{{ item.timestamp }}</div>
      </div>
    </li>
  </div>
</template>

<script>
export default {
  data() {
    return {
      //last several voted users
      voters: [
        {
          id: 1,
          name: "Ronald Richards",
          option: "Michael Jordan",
          timestamp: "2022-03-23 07:10:15",
        },
        {
          id: 2,
          name: "Devon Lane",
          option: "Lin Dan",
          timestamp: "2022-03-23 07:10:15",
        },
        {
          id: 3,
          name: "Jacob Jones",
          option: "Lionel Messi",
          timestamp: "2022-03-23 07:10:15",
        },
        {
          id: 4,
          name: "Jane Copper",
          option: "Usain Bolt",
          timestamp: "2022-03-23 07:10:15",
        },
        {
          id: 5,
          name: "Albert Flores",
          option: "Michael Phelps",
          timestamp: "2022-03-23 07:10:15",
        },
        {
          id: 6,
          name: "Ronald Richards",
          option: "Michael Jordan",
          timestamp: "2022-03-23 07:10:15",
        },
        {
          id: 7,
          name: "Devon Lane",
          option: "Lin Dan",
          timestamp: "2022-03-23 07:10:15",
        },
        {
          id: 8,
          name: "Jacob Jones",
          option: "Lionel Messi",
          timestamp: "2022-03-23 07:10:15",
        },
        {
          id: 9,
          name: "Jane Copper",
          option: "Usain Bolt",
          timestamp: "2022-03-23 07:10:15",
        },
      ],
    };
  },
};
</script>

<style scoped>
page {
  background-color: #f5f5f5;
}
li {
  list-style: none;
}
.voterList {
  margin-top: 150px;
  margin-left: auto;
  margin-right: auto;
}
.voter {
  /* width: 500px; */
  /* height: 50px; */
  background-color: transparent;
  display: flex;
  line-height: 50px;
  font-family: "PingFang SC";
  font-style: normal;
  font-weight: 600;
  font-size: 15px;
  margin: 10px 0;
}
.icon {
  margin: 10px 10px 0 10px;
  width: 30px;
  height: 30px;
  border-radius: 50%;
  background-color: #165dff;
  line-height: 31px;
  color: #fff;
  text-align: center;
  font-weight: bold;
  font-size: 20px;
}
.timestamp {
  margin-left: 10px;
  font-family: "PingFang SC";
  font-style: normal;
  font-weight: 400;
  font-size: 12px;
  color: #999999;
}
.voted {
  width: 55px;
  font-size: 12px;
  color: #999999;
  text-align: center;
}
</style>
