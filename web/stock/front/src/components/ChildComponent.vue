<template>
  <div :style="getStyle"></div>

</template>

<script setup lang="ts">
import { CSSProperties,computed } from "vue";
interface IImgSize{
  size:{
    width:number,
    height:number
  }
}
const props = withDefaults(defineProps<IImgSize>(),{
  size:{
    width:200,
    height:20
  }
})
const getStyle = computed(
    (): CSSProperties => {
      const { size } = props;
      return {
        width: size.width + 'px',
        height: size.height + 'px',
      };
    }
);

</script>
<style scoped>
div{
  background-color: red;
}
</style>
