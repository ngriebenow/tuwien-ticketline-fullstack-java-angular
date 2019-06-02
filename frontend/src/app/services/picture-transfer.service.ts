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
    this.uploadedPictureIds = new Array();
    this.pictures = new Array();
  }

  /**
   * Append an picture id to an array
   * @param data id of the picture
   */
  pushPicture(picture: Picture) {
    this.pictures.push(picture);
  }

  /**
   * Get all picture Ids
   * @return pictureIds
   */
  uploadData() {
    let promises: any[];
    promises = new Array();
    for (const picture of this.pictures) {
      // alert('picture transfer service, upload picture' + file.name);
      const myData = this.pictureService.uploadPicture(picture.file).toPromise();
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
    this.pictures = new Array();
  }
  getPictures(): Picture[] {
    return this.pictures;
  }
  deletePicture(picture: Picture) {
    const index = this.pictures.indexOf(picture);
    if (index > -1) {
      this.pictures.splice(index, 1);
    }
  }
}
