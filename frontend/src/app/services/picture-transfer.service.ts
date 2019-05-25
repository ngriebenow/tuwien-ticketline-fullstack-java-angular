import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class PictureTransferService {
  private uploadedPictureIds: number[];
  constructor() {
    this.uploadedPictureIds = new Array();
  }
  appendData(data) {
    this.uploadedPictureIds.push(data);
  }

  getData() {
    const temp = this.uploadedPictureIds;
    this.clearData();
    return temp;
  }

  clearData() {
    this.uploadedPictureIds = undefined;
  }
}
