import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HomeComponent } from './home.component';

const usersModule = () => import('@app/users/users.module').then(x => x.UsersModule);


const routes: Routes = [        
        { path: '', component: HomeComponent, },
        { path: 'users', loadChildren: usersModule },
      
    
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class HomeRoutingModule { }