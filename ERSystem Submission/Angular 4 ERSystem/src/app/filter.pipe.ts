import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'filter'
})
export class FilterPipe implements PipeTransform {

  transform(reimbursement: any, status: any): any {
    // check if search term is undefined
    if (status === undefined) {
      return reimbursement;
    }
  }

}
