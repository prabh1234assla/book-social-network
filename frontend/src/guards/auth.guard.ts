import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';

export const authGuard: CanActivateFn = (route, state) => {
  const token = localStorage.getItem('token');
  const router = inject(Router);

  const publicRoutes = ['/login', '/register'];

  if (publicRoutes.includes(state.url)) {
    return true;
  }

  if (token) {
    return true;
  }

  return router.parseUrl('/register');
};
