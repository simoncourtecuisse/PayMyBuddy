import { NgModule } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

import { ContactsRoutingModule } from './contacts-routing.module';
import { LayoutContactsComponent } from './layout-contacts.component';
import { ListContactsComponent } from './list-contacts.component';
import { AddEditContactsComponent } from './add-edit-contacts.component';

@NgModule({
    imports: [
        CommonModule,
        ReactiveFormsModule,
        ContactsRoutingModule
    ],
    declarations: [
        LayoutContactsComponent,
        ListContactsComponent,
        AddEditContactsComponent
    ]
})
export class ContactsModule { }