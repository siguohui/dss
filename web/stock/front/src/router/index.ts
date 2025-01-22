import {createRouter, createWebHistory} from "vue-router";

// 创建一个路由
const router = createRouter({
        // vue3路由必须指定工作模式
        history: createWebHistory(),
        // 定义规则和vue2一样
        routes: [

            {
                path: "/home", // 路径
                component: () => import("@/views/Home/index.vue")
            }
        ]
    }
)

export default router
