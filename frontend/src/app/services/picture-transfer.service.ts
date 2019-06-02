import { Injectable } from '@angular/core';
import {PictureService} from './picture.service';

@Injectable({
  providedIn: 'root'
})
export class PictureTransferService {
  private uploadedPictureIds: number[];
  private files: File[];
  constructor(private pictureService: PictureService) {
    this.uploadedPictureIds = new Array();
    this.files = new Array();
  }

  /**
   * Append an picture id to an array
   * @param data id of the picture
   */
  appendFile(file: File) {
    this.files.push(file);
  }

  /**
   * Get all picture Ids
   * @return pictureIds
   */
  uploadData() {
    let promises: any[];
    promises = new Array();
    for (const file of this.files) {
      // alert('picture transfer service, upload picture' + file.name);
      const myData = this.pictureService.uploadPicture(file).toPromise();
      myData.then(function (result) {
        this.uploadedPictureIds.push(result);
      }.bind(this));
      promises.push(myData);
    }
    return promises;
  }
  getIds(): number[] {
    return this.uploadedPictureIds;
  }
  /**
   * Clears the pictureIds array.
   */
  clearData() {
    this.uploadedPictureIds = new Array();
    this.files = new Array();
  }
}
