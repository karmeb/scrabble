import { Routes } from '@angular/router';

export const routes: Routes = [
    {
        path: '',
        pathMatch: 'full',
        loadComponent: async () => {
            const module = await import('./pages/home/home.component');
            return module.HomeComponent;
        },
    },
    {
        path: 'add-word',
        loadComponent: async () => {
            const module = await import('./pages/add-word/add-word.component');
            return module.AddWordComponent;
        },
    },
    {
        path: '**',
        loadComponent: async () => {
            const module = await import('./pages/not-found/not-found.component');
            return module.NotFoundComponent;
        },
    },
];
