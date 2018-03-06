import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Reimbursement } from '../../beans/reimbursement';
import { Router } from '@angular/router';

@Component({
  selector: 'app-add-reimbursements',
  templateUrl: './add-reimbursements.component.html',
  styleUrls: ['./add-reimbursements.component.css']
})
export class AddReimbursementsComponent implements OnInit {

  // newReimbursement = new Reimbursement();

  newReimbursement = {
    amount: '',
    description: '',
    type: 0
  };

  constructor(private client: HttpClient, private router: Router) { }

  ngOnInit() {
    // this.newReimbursement.type = 2;
  }

  addReimbursement() {
    console.log('adding reimbursement');
    this.client.post('http://localhost:8080/ERSystem/reimbursement', this.newReimbursement, {withCredentials: true})
    .subscribe(
      (succ) => {
        alert('Reimbursement Submitted');
        this.router.navigateByUrl('/reimbursement');
      },
      (err) => {
        alert('Reimbursement failed to be Submitted');
      }
    );

  }

}
