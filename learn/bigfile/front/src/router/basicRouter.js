
// 基础路由
export const basicRoutes=
    [
        {
            path: '/',
            name: 'Login',
            component: () => import('@/views/login/index.vue')
        },
        {
            path: '/home',
            name: 'Home',
            component: () => import('@/views/home/index.vue')
        }
    ]


//     [
//     {
//         path: '/',
//         name: 'Root',
//         redirect: '/question',
//         meta: {
//             title: '首页',
//             hideBreadcrumb: true,
//             hideMenu: true
//         }
//     },
//     {
//         path: '/login',
//         name: 'Login',
//         component: () => import('@/views/login/index.vue'),
//         meta: {
//             title: '登录',
//             hideBreadcrumb: true,
//             hideMenu: true
//         }
//     },
//     {
//         path: '/redirect/:path(.*)/:_redirect_type(.*)/:_origin_params(.*)?',
//         component: () => import('@/views/redirect/index.vue'),
//         name: 'RedirectTo',
//         meta: {
//             title: '重定向',
//             hideBreadcrumb: true,
//             hideMenu: true
//         }
//     },
//     {
//         path: '/error',
//         name: 'Error',
//         component: LAYOUT,
//         redirect: '/error/403',
//         meta: {
//             title: '异常页面',
//             hideBreadcrumb: true,
//             hideMenu: true
//         },
//         children: [
//             {
//                 path: '/error/:code(403|404|500)',
//                 name: '页面异常',
//                 component: () => import('@/views/error/index.vue'),
//                 meta: {
//                     title: '403'
//                 }
//             }
//         ]
//     },
//     {
//         path: '/:path(.*)*',
//         name: '404',
//         redirect: '/error/404',
//         meta: {
//             title: 'ErrorPage',
//             hideBreadcrumb: true,
//             hideMenu: true
//         }
//     }
// ]
