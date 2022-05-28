import { Component, OnInit } from '@angular/core';
import { AccountService } from '@app/_services';
//import { UserRoleService } from '@app/_services/userRole.service';
import { first } from 'rxjs/operators';

@Component({
  selector: 'app-board-admin',
  templateUrl: './board-admin.component.html'
})
export class BoardAdminComponent implements OnInit {
  users = null;
  // user: any;
  content = '';
  constructor(
    private accountService: AccountService,
    // private userRoleService: UserRoleService
    ) { }
  ngOnInit() {
    // this.userRoleService.getAdminBoard().subscribe(
    //   data => {
    //     this.content = data;
    //   },
    //   err => {
    //     this.content = JSON.parse(err.error).message;
    //   }
    // );

    this.accountService.getAll()
      .pipe(first())
      .subscribe(users => this.users = users);
  }
}