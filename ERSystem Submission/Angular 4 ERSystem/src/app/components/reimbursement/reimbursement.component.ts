import { Component, OnInit } from '@angular/core';
import { Reimbursement } from '../../beans/reimbursement';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';

@Component({
  selector: 'app-reimbursement',
  templateUrl: './reimbursement.component.html',
  styleUrls: ['./reimbursement.component.css']
})
export class ReimbursementComponent implements OnInit {

  reimbursements: Array<Reimbursement> = [];

  constructor(private client: HttpClient) { }

  ngOnInit() {
    this.client.get('http://localhost:8080/ERSystem/reimbursement',
      {withCredentials: true})
      .subscribe(
        (succ: Array<Reimbursement>) => {
          this.reimbursements = succ;
        },
        (err) => {
          console.log('failed to load reimbursements');
        }
      );
  }
}
