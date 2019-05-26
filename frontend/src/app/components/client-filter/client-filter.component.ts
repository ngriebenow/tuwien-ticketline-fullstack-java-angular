import {Component, OnInit} from '@angular/core';
import {FormBuilder} from '@angular/forms';
import {ClientFilter} from '../../dtos/client-filter';
import {Client} from '../../dtos/client';
import {ClientService} from '../../services/client.service';

@Component({
  selector: 'app-user-filter',
  templateUrl: './client-filter.component.html',
  styleUrls: ['./client-filter.component.scss']
})
export class ClientFilterComponent implements OnInit {

  public clients: Client[];
  private page = 0;
  private count = 20;
  private queryParams: ClientFilter;

  private searchForm = this.formBuilder.group({
    clientName: [''],
    clientSurname: [''],
    clientEmail: ['']
  });

  constructor(private clientService: ClientService, private formBuilder: FormBuilder) {
    this.queryParams = new ClientFilter('', '', '', 0, 10);
  }

  ngOnInit() {
    this.loadClients();
  }

  private loadClients(): void {
    this.queryParams.page = this.page;
    this.queryParams.count = this.count;
    this.clientService.getClientsFiltered(this.queryParams).subscribe(
      (client: Client[]) => {
        this.clients = client;
        this.clients.forEach(function (value) {
          console.log(value);
        });
      },
      error => {
        // TODO: error handling
      }
    );
  }

  private nextPage(): void {
    this.page++;
    this.loadClients();
  }

  private previousPage(): void {
    if (this.page > 0) {
      this.page--;
      this.loadClients();
    }
  }

  public update(): void {
    this.loadClients();
  }


}
