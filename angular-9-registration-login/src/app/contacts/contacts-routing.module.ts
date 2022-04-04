import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { LayoutContactsComponent } from './layout-contacts.component';
import { ListContactsComponent } from './list-contacts.component';
import { AddEditContactsComponent } from './add-edit-contacts.component';

const routes: Routes = [
    {
        path: '', component: LayoutContactsComponent,
        children: [
            { path: '', component: ListContactsComponent },
            { path: 'add', component: AddEditContactsComponent },
            { path: 'edit/:id', component: AddEditContactsComponent }
        ]
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class ContactsRoutingModule { }