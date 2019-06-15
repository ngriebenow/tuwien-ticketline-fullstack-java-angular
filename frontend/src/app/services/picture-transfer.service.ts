import { Injectable } from '@angular/core';
import {PictureService} from './picture.service';
import {Picture} from '../dtos/picture';

@Injectable({
  providedIn: 'root'
})
export class PictureTransferService {
  private uploadedPictureIds: number[];
  private pictures: Picture[];
  constructor(private pictureService: PictureService) {
    this.uploadedPictureIds = [];
    this.pictures = [];
  }

  /**
   * Append a picture to an array.
   * @param picture to be appended
   */
  pushPicture(picture: Picture) {
    this.pictures.push(picture);
  }

  /**
   * Initiate post request to backend for all current pictures and push the returned ids.
   * @return promises of all request so that news entry can be created after all requests are done.
   */
  uploadData() {
    let promises: any[];
    promises = [];
    for (const picture of this.pictures) {
      const myData = this.pictureService.uploadPicture(picture.file).toPromise();
      myData.then(function (result) {
        this.uploadedPictureIds.push(result);
      }.bind(this));
      promises.push(myData);
    }
    return promises;
  }
  /**
   * Return the array of all picture ids.
   * @return uploadedPictureIds
   */
  getIds(): number[] {
    return this.uploadedPictureIds;
  }
  /**
   * Clears the pictureIds array.
   */
  clearData() {
    this.uploadedPictureIds = [];
    this.pictures = [];
  }
  /**
   * Get the current picture array of pictures (for rendering as thumbnail)
   * @return pictures array
   */
  getPictures(): Picture[] {
    return this.pictures;
  }
  /**
   * Delete a dropped in picture.
   * @param picture to be deleted
   */
  deletePicture(picture: Picture) {
    const index = this.pictures.indexOf(picture);
    if (index > -1) {
      this.pictures.splice(index, 1);
    }
  }
}
