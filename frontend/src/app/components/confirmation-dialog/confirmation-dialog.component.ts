import {Component, OnInit, EventEmitter, Output} from '@angular/core';

@Component({
  selector: 'app-confirmation-dialog',
  templateUrl: './confirmation-dialog.component.html',
  styleUrls: ['./confirmation-dialog.component.scss']
})
export class ConfirmationDialogComponent implements OnInit {

  text: string;
  @Output() yes = new EventEmitter<any>();
  @Output() no = new EventEmitter<any>();

  constructor() {
  }

  ngOnInit() {
  }

  accept(): void {
    this.yes.emit(null);
  }

  decline(): void {
    this.no.emit(null);
  }

}
