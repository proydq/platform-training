<script setup>
defineProps({
  number: String,
  label: String
})
</script>
<template>
  <el-card class="stat-card" shadow="hover">
    <div class="stat-number">{{ number }}</div>
    <div class="stat-label">{{ label }}</div>
  </el-card>
</template>
<style scoped>
.stat-number {
  font-size: 32px;
  font-weight: 700;
  color: #667eea;
  margin-bottom: 5px;
}
.stat-label {
  color: #666;
  font-size: 14px;
}
</style>
