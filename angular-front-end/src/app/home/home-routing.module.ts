import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AuthGuard } from '@app/_helpers/auth.guard';
import { HomeComponent } from './home.component';

const profileModule = () => import('@app/users/profile.module').then(x => x.ProfileModule);


const routes: Routes = [        
        { path: '', component: HomeComponent, canActivate: [AuthGuard]},
        { path: 'profile', loadChildren: profileModule },
      
    
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class HomeRoutingModule { }