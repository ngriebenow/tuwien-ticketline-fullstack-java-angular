import {Component, OnInit} from '@angular/core';
import {FormBuilder} from '@angular/forms';
import {ClientFilter} from '../../dtos/client-filter';
import {Client} from '../../dtos/client';
import {ClientService} from '../../services/client.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-user-filter',
  templateUrl: './client-filter.component.html',
  styleUrls: ['./client-filter.component.scss']
})
export class ClientFilterComponent implements OnInit {

  public clients: Client[];
  public page = 0;
  public count = 20;
  public queryParams: ClientFilter;
  public customerID: string;

  public searchForm = this.formBuilder.group({
    clientName: [''],
    clientSurname: [''],
    clientEmail: ['']
  });

  constructor(private clientService: ClientService, private formBuilder: FormBuilder, private router: Router) {
    this.queryParams = new ClientFilter('', '', '', 0, 10);
  }

  ngOnInit() {
    this.loadClients();
  }

  public loadClients(): void {
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

  public loadClient(): void {
    this.clientService.getClientById(this.customerID).subscribe(
      (client) => {
        this.clients = new Array();
        this.clients.push(client);
      },
      error => {
        // TODO: error handling
      }
    );
  }

  public nextPage(): void {
    this.page++;
    this.loadClients();
  }

  public previousPage(): void {
    if (this.page > 0) {
      this.page--;
      this.loadClients();
    }
  }

  public update(): void {
    if (this.customerID === '') {
      this.loadClients();
    } else {
      this.loadClient();
    }

  }

  selectUser(client: Client): void {
    localStorage.setItem('client', JSON.stringify(client));
  }

  anonym(): void {
    this.clientService.getClientById('0').subscribe(
      (c) => {
        this.selectUser(c);
      },
      error => {
      }
    );
  }

  public addClient(): void {
    this.router.navigate(['/client-add']);
  }

}
