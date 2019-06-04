import { Component, OnInit, Input } from '@angular/core';
import { Picture } from '../../dtos/picture';
import {PictureService} from '../../services/picture.service';
import { Lightbox } from 'ngx-lightbox';

@Component({
  selector: 'app-picture-display',
  templateUrl: './picture-display.component.html',
  styleUrls: ['./picture-display.component.scss']
})
export class PictureDisplayComponent implements OnInit {
  @Input() ids: number[] = new Array();
  pictures: Picture[];
  private albums: any[];
  private promisesLoaded: any[];
  private promisesConverted: any[];
  constructor(private pictureService: PictureService,
              private lightbox: Lightbox) {
    this.pictures = new Array();
    this.albums = new Array();
    this.promisesLoaded = new Array();
    this.promisesConverted = new Array();
  }
  ngOnInit() {
    this.loadAllPictures();
  }
  /**
   * load all pictures with ids, one by one from service.
   */
  loadAllPictures() {
    for (const id of this.ids) {
      this.loadPictureById(id);
    }
    Promise.all(this.promisesLoaded).then(function (resultLoaded) {
      Promise.all(this.promisesConverted).then(function (resultConverted) {
        const sortedPicture = this.pictures.sort((a, b) => a.id - b.id);
        for (const picture of sortedPicture) {
          const src = picture.data;
          const caption = '';
          const thumb = picture.data;
          const album = {
            src: src,
            caption: caption,
            thumb: thumb
          };
          this.albums.push(album);
        }
      }.bind(this));
    }.bind(this));
  }
  /**
   * load a picture with given id from service and append it to pictures.
   * @param id of the picture
   */
  loadPictureById(id: number) {
    console.log('Load picture with id ' + id);
    const promise = this.pictureService.getPicture(id).toPromise();
    promise.then(function (result) {
      this.appendPictureFromBlob(id, result);
    }.bind(this));
    this.promisesLoaded.push(promise);
  }
  /**
   * convert the blob from db and append the result to pictures.
   * @param id of the picture.
   * @param picture the loaded blob from db.
   */
  appendPictureFromBlob(id: number, picture: Blob) {
    const reader = new FileReader();
    const promise = new Promise(function (resolve) {
      reader.addEventListener('load', () => {
        resolve(new Picture(id, null, reader.result));
      }, false);
    }.bind(this));
    promise.then(function (result) {
      this.pictures.push(result);
    }.bind(this));
    this.promisesConverted.push(promise);
    if (picture) {
      reader.readAsDataURL(picture);
    }
  }
  /**
   * return sorted pictures array.
   * @return picture array sorted by id ascending
   */
  getAlbums(): any[] {
    return this.albums;
  }

  open(index: number): void {
    // open lightbox
    this.lightbox.open(this.albums, index);
  }
  close(): void {
    // close lightbox programmatically
    this.lightbox.close();
  }

}
