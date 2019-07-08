import Vue from 'vue'
import Router from 'vue-router'
import Index from '@/Index'
import SiteCards from '@/components/SiteCards'
import SiteManage from '@/components/SiteManage'
import GroupManage from '@/components/GroupManage'
import SiteEdit from '@/components/SiteEdit'
import NotFound from '@/components/404'
import Main from '@/components/Main'

Vue.use(Router)

const mainRouter = new Router({
    routes: [
        {
            name: 'index',
            path: '/',
            hidden: true,
            component: Index,
            children: [
                {
                    name: 'main-page',
                    path: '/index',
                    component: Main
                },
                {
                    name: 'site-group',
                    path: '/group/:groupId',
                    component: SiteCards,
                    props: true
                },
                {
                    name: 'site-manage',
                    path: '/site-manage',
                    component: SiteManage
                },
                {
                    name: 'site-edit',
                    path: '/manage/site-edit/:siteId',
                    component: SiteEdit
                },
                {
                    name: 'site-add',
                    path: '/manage/add-site',
                    component: SiteEdit
                },
                {
                    name: 'group-manage',
                    path: '/group-manage',
                    component: GroupManage
                }
            ]
        },
        {
            path: '/404',
            component: NotFound,
            name: '404',
            hidden: true
        },
        {
            path: '*',
            hidden: true,
            redirect: { path: '/404' }
        }

    ]

})

export default mainRouter