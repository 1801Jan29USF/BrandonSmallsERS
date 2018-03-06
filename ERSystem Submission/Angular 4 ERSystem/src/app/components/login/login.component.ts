import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { User } from '../../beans/user';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})

export class LoginComponent implements OnInit {
  credential = {
    username: '',
    password: ''
  };

  constructor(private client: HttpClient, private router: Router, private user: User) { }

  ngOnInit() {
  }
  login() {
    this.client.post('http://localhost:8080/ERSystem/login', this.credential, {withCredentials: true})
      .subscribe(
        data => {
          this.user = <User>data;
          console.log(data);
          alert(this.credential.username + ' logged in');
          if (this.user.roleID === 2) {
            this.router.navigateByUrl('/reimbursement');
          } else if (this.user.roleID === 1) {
            this.router.navigateByUrl('/managers');
          }
        },
        err => {
          alert('failed to log in');
        }
      );
  }
}

