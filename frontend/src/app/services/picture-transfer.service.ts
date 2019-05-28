import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class PictureTransferService {
  private uploadedPictureIds: number[];
  constructor() {
    this.uploadedPictureIds = new Array();
  }

  /**
   * Append an picture id to an array
   * @param data id of the picture
   */
  appendData(data) {
    this.uploadedPictureIds.push(data);
  }

  /**
   * Get all picture Ids
   * @return pictureIds
   */
  getData() {
    const temp = this.uploadedPictureIds;
    this.clearData();
    return temp;
  }

  /**
   * Clears the pictureIds array.
   */
  clearData() {
    this.uploadedPictureIds = new Array();
  }
}
