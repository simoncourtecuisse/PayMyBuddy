import { Component, OnInit } from '@angular/core';
import { first } from 'rxjs/operators';

import { AccountService } from '@app/_services';

@Component({ templateUrl: 'list-contacts.component.html' })
export class ListContactsComponent implements OnInit {
    contacts = null;

    constructor(private accountService: AccountService) {}

    ngOnInit() {
        this.accountService.getAll()
            .pipe(first())
            .subscribe(contacts => this.contacts = contacts);
    }

    deleteUser(id: string) {
        const user = this.contacts.find(x => x.id === id);
        user.isDeleting = true;
        this.accountService.delete(id)
            .pipe(first())
            .subscribe(() => {
                this.contacts = this.contacts.filter(x => x.id !== id) 
            });
    }
}