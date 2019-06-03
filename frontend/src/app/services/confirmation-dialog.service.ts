import {Injectable} from '@angular/core';

import {NgbModal, NgbModalRef} from '@ng-bootstrap/ng-bootstrap';

import {ConfirmationDialogComponent} from '../components/confirmation-dialog/confirmation-dialog.component';

@Injectable({
  providedIn: 'root'
})
export class ConfirmationDialogService {

  private modal: NgbModalRef;
  private onYes: () => void;
  private onNo: () => void;

  constructor(private modalService: NgbModal) {
  }

  public open(question: string, onYes: () => void, onNo?: () => void): void {
    this.close();
    this.modal = this.modalService.open(ConfirmationDialogComponent);
    this.modal.componentInstance.text = question;
    this.onYes = onYes;
    this.onNo = onNo;

    this.modal.componentInstance.yes.subscribe(_ => {
      if (this.onYes !== undefined && this.onYes !== null) {
        this.onYes();
      }
      this.close();
    });

    this.modal.componentInstance.no.subscribe(_ => {
      if (this.onNo !== undefined && this.onNo !== null) {
        this.onNo();
      }
      this.close();
    });
  }

  public close(): void {
    if (this.modal !== undefined && this.modalService.hasOpenModals) {
      this.modal.dismiss();
    }
  }
}
