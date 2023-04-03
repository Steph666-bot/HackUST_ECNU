<template>
  <div class="resultBox">
    <div class="question">Who is the GOAT sports player?</div>
    <div class="itemsBox">
      <li v-for="item in results">
        <div class="item">
          <div class="ladder" :style="ladderWidth(item.count)">
            <div class="name">{{ item.doc_identifier }}</div>
          </div>
          <div class="count">{{ item.count }}</div>
        </div>
      </li>
    </div>
    <div class="bottomBox">
      <div class="thanks">Thanks for your vote.</div>
      <div class="data">{{ this.sum }} people voted</div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {};
  },
  props: {
    results: [], //result data
    sum: Number, // sum vote
    max: Number, // max vote
  },

  methods: {
    //calculate the width of voted part
    ladderWidth(count) {
      let length = (220 * count) / this.max + 180;
      return `width: ${length}px;`;
    },
  },
};
</script>

<style scoped>
page {
  background-color: #f5f5f5;
}
.resultBox {
  display: block;
  margin-left: auto;
  margin-right: auto;
}
.question {
  margin-top: 130px;
  margin-left: auto;
  margin-right: auto;
  text-align: center;
  font-size: 35px;
  font-weight: bold;
}
.itemsBox {
  width: 500px;
  margin: 0 auto;
  margin-top: 50px;
}
.item {
  margin: 20px 0;
  width: 400px;
  height: 60px;
  background-color: #165dff;
  display: flex;
  box-shadow: 0px 2px 16px rgba(102, 102, 102, 0.05);
  border-radius: 4px;
  border: 3px solid rgba(22, 93, 255, 0.8);
}
.ladder {
  width: 200px;
  height: 0;
  border-width: 0px 30px 60px 0px;
  border-style: none solid solid;
  border-color: transparent transparent #fff;
}
.name {
  line-height: 60px;
  font-size: 20px;
  font-weight: bold;
  padding-left: 20px;
}
.count {
  line-height: 60px;
  font-size: 20px;
  font-weight: bold;
  color: #fff;
}
li {
  list-style: none;
}
.bottomBox {
  margin: 0 auto;
  width: 500px;
  display: flex;
}
.data {
  position: relative;
  right: -45px;
  margin-top: 10px;
  font-family: "PingFang SC";
  font-style: normal;
  font-weight: 400;
  font-size: 18px;
  line-height: 25px;
  color: #999999;
}
.thanks {
  line-height: 40px;
  font-size: 20px;
  border-bottom: 3px solid #165dff;
}
</style>
