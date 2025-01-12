import {createRouter, createWebHistory} from "vue-router";

const routes = [
    {   path: '/home',
        component: () => import("@/views/home/index.vue"),
        meta: { title: 'About Page', requiresAuth: true },
        children: [
            {
                path: '',
                component: () => import("@/views/home/Overview.vue")
            },
            {
                path: '/profile',
                component: () => import("@/views/home/Profile.vue"),
                meta: { title: 'About Page', requiresAuth: true }
            }
        ] },
    { path: '/', component: () => import("@/views/login/index.vue") },
];

// 创建一个路由
const router = createRouter({
        history: createWebHistory(),
        routes: routes
    }
)

router.beforeEach((to, from, next) => {
    console.log('to', to)
    if (to.matched.some(record => record.meta.requiresAuth)) {
        // 检查用户是否已登录
        // if (!isUserLoggedIn()) {
        //     next('/login');
        // } else {
        //     next();
        // }
        //this.$router.push('/about');
        next();
    } else {
        next();
    }
});

export default router

// createWebHistory：基于 HTML5 的历史模式（推荐用于单页应用）。
// createWebHashHistory：哈希模式（/#/about）。
// createMemoryHistory：无 URL 支持的内存模式（例如用于服务端渲染）。

// beforeEach: 全局前置守卫，在路由切换之前调用。
// beforeResolve: 全局解析守卫，在路由被确认之前调用。
// afterEach: 全局后置守卫，在路由切换之后调用。
// beforeEnter: 路由独享守卫，在路由进入之前调用。
// beforeRouteUpdate: 路由更新守卫，在当前路由复用组件之前调用。
// beforeRouteLeave: 路由离开守卫，在当前路由离开之前调用。
