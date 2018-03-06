import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddReimbursementsComponent } from './add-reimbursements.component';

describe('AddReimbursementsComponent', () => {
  let component: AddReimbursementsComponent;
  let fixture: ComponentFixture<AddReimbursementsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddReimbursementsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddReimbursementsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
