<script setup>
import StatsCard from '../components/StatsCard.vue'
const stats = [
  { number: '24', label: '已完成课程' },
  { number: '8', label: '待学习课程' },
  { number: '95%', label: '考试通过率' },
  { number: '156', label: '学习积分' }
]
</script>
<template>
  <div>
    <el-row :gutter="20" class="stats-grid">
      <el-col :xs="12" :sm="6" v-for="(item, index) in stats" :key="index">
        <StatsCard :number="item.number" :label="item.label" />
      </el-col>
    </el-row>
    <div class="main-placeholder">仪表板内容...</div>
  </div>
</template>
<style scoped>
.stats-grid {
  margin-bottom: 20px;
}
.main-placeholder {
  background: #fff;
  padding: 40px;
  border-radius: 10px;
  text-align: center;
}
</style>
