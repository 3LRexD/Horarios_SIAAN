import { createRouter, createWebHistory } from 'vue-router'
import Login from '../views/Login.vue'
import SIAAN from '../views/SIAAN.vue'
import Horario from '../views/Horario.vue'

const routes = [
  {
    path: '/',
    redirect: '/login'
  },
  {
    path: '/login',
    name: 'Login',
    component: Login
  },
  {
    path: '/siaan',
    name: 'SIAAN',
    component: SIAAN,
    meta: { requiresAuth: true }
  },
  {
    path: '/horario',
    name: 'Horario',
    component: Horario,
    meta: { requiresAuth: true }
  },
  {
    path: '/:pathMatch(.*)*',
    redirect: '/login'
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

router.beforeEach((to, from, next) => {
  const userData = localStorage.getItem("usuario");
  
  if (to.meta.requiresAuth && !userData) {
    next('/login');
  } else {
    next();
  }
})

export default router