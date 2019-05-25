import { Component, OnInit, Input } from '@angular/core';
import { Picture } from '../../dtos/picture';
import {PictureService} from '../../services/picture.service';

@Component({
  selector: 'app-picture-display',
  templateUrl: './picture-display.component.html',
  styleUrls: ['./picture-display.component.scss']
})
export class PictureDisplayComponent implements OnInit {
  @Input() ids: number[];
  pictures: Picture[];
  constructor(private pictureService: PictureService) {
    this.pictures = new Array();
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
  }
  /**
   * load a picture with given id from service and append it to pictures.
   * @param id of the picture
   */
  loadPictureById(id: number) {
    console.log('Load picture with id ' + id);
    this.pictureService.getPicture(id).subscribe(data => {
      this.appendPictureFromBlob(id, data);
    }, error => {
      console.log(error);
    });
  }
  /**
   * convert the blob from db and append the result to pictures.
   * @param id of the picture.
   * @param picture the loaded blob from db.
   */
  appendPictureFromBlob(id: number, picture: Blob) {
    const reader = new FileReader();
    reader.addEventListener('load', () => {
      this.pictures.push(new Picture(id, reader.result));
    }, false);

    if (picture) {
      reader.readAsDataURL(picture);
    }
  }
  /**
   * return sorted pictures array.
   * @return picture array sorted by id ascending
   */
  getPicturesSortedById(): Picture[] {
    return this.pictures.sort((a, b) => a.id - b.id);
  }

}
