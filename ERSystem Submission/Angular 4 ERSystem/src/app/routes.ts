import { Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { HomeComponent } from './components/home/home.component';
import { Component } from '@angular/core';
import { ReimbursementComponent } from './components/reimbursement/reimbursement.component';
import { ManagersComponent } from './components/managers/managers.component';
import { AddReimbursementsComponent } from './components/add-reimbursements/add-reimbursements.component';

export const appRoutes: Routes = [
  {
      path: 'home',
      component: HomeComponent
  },
  {
    path: 'login',
    component: LoginComponent
  },
  {
    path: 'reimbursement',
    component: ReimbursementComponent
  },
  {
    path: 'managers',
    component: ManagersComponent
  },
  {
    path: 'add-reimbursements',
    component: AddReimbursementsComponent
  },
  {
    path: '**',
    pathMatch: 'full',
    redirectTo: 'home'
  }
];
