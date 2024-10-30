
import { createRouter, createWebHistory} from 'vue-router'
import { basicRoutes } from './basicRouter'

// 设置路由白名单
const WHITE_NAME_LIST = []
// 白名单应该包含基本静态路由
const getRouteNames = (array) =>
    array.forEach(item => {
        WHITE_NAME_LIST.push(item.name)
        getRouteNames(item.children || [])
    })
getRouteNames(basicRoutes)
// 导入modules目录下的所有的路由 免除手动导入
const modules = import.meta.glob('./modules/**/*.ts', { eager: true })
const routeModuleList = []
// 加入到路由集合中
Object.keys(modules).forEach(key => {
    const mod = modules[key].default || {}
    const modList = Array.isArray(mod) ? [...mod] : [mod]
    routeModuleList.push(...modList)
})
// 权限路由处理 此处代码根据业务需求进行变更
export const asyncRoutes = [...routeModuleList]
// 创建路由
export const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: basicRoutes.concat(asyncRoutes),
    strict: true,
    scrollBehavior: () => ({ left: 0, top: 0 })
})

// 初始化路由
export function initRouter() {
    router.getRoutes().forEach(route => {
        const { name } = route
        if (name && !WHITE_NAME_LIST.includes(name)) {
            router.hasRoute(name) && router.removeRoute(name)
        }
    })
}

// 切换角色之后 刷新路由
export function resetRouter() {
    router.getRoutes().forEach(route => {
        const { name } = route
        if (name && !WHITE_NAME_LIST.includes(name)) {
            router.hasRoute(name) && router.removeRoute(name)
        }
    })
}
// 登录之后 添加异步路由
export function AddSyncRouter() {
    router.getRoutes().forEach(route => {
        const { name } = route
        if (name && !WHITE_NAME_LIST.includes(name)) {
            router.hasRoute(name) && router.removeRoute(name)
        }
    })
}
// 配置路由器
export function setupRouter(app) {
    app.use(router)
}
