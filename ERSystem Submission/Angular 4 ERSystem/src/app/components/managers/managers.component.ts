import { Component, OnInit } from '@angular/core';
import { Reimbursement } from '../../beans/reimbursement';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';


@Component({
  selector: 'app-managers',
  templateUrl: './managers.component.html',
  styleUrls: ['./managers.component.css']
})
export class ManagersComponent implements OnInit {

  reimbursements: Array<Reimbursement> = [];

  updateRequest = {
    status: 0,
    id: 0
  };

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

  updateStatus(status: number, id: number) {
    this.updateRequest.id = id;
    this.updateRequest.status = status;
    this.client.post('http://localhost:8080/ERSystem/update', this.updateRequest, { withCredentials: true })
    .subscribe (
      (succ) => {
        if (this.updateRequest.status === 2) {
          alert('Reimbursement Approved');
           this.ngOnInit();
        } else if (this.updateRequest.status === 3) {
          alert('Reimbursement Denied');
           this.ngOnInit();
        }
      },
      (err) => {
        console.log('Failed to update');
      }
    );
  }

}
