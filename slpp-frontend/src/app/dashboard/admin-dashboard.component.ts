import { Component, Inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AdminService } from '../services/admin.service';
import { PetitionService } from '../services/petition.service';
import { AuthService } from '../services/auth.service';
import { Router, RouterModule } from '@angular/router';
import { NgChartsModule } from 'ng2-charts';
import { ChartOptions, ChartType, ChartData } from 'chart.js';
import { NgxChartsModule } from '@swimlane/ngx-charts';
import { WordCloudComponent } from "../word-cloud.component";

declare var bootstrap: any;

@Component({
  standalone: true, 
    imports: [CommonModule, FormsModule, NgChartsModule, NgxChartsModule, WordCloudComponent],
    templateUrl: './admin-dashboard.component.html'
})
export class AdminDashboardComponent implements OnInit {
  threshold = 1000;
  petitions: any[] = [];
  responseInput = '';
  selectedPetitionId: number | null = null;

  modal: any;

  //wordCloudData: { text: string; weight: number }[] = [];
  wordCloudData = [
    { text: 'Environment', size: 10 },
    { text: 'Education', size: 7 },
    { text: 'Healthcare', size: 5 },
    { text: 'Economy', size: 2 },
  ];

  // Chart Config
  chartOptions: ChartOptions = {
    responsive: true,
    plugins: {
      legend: {
        display: true,
      },
      title: {
        display: true,
        text: 'Petition Signatures Overview',
      },
    }
  };
  chartLabels: string[] = [];
  chartData: ChartData<'bar'> = {
    labels: [],
    datasets: [
      { data: [], label: 'Signatures', backgroundColor: '#007bff' }
    ]
  };
  chartType: ChartType = 'bar';

  constructor(
    @Inject(Router) private router: Router,
    private adminService: AdminService,
    private petitionService: PetitionService,
    private authService: AuthService
  ) {}

  logout(): void {
    this.authService.clearUserData();
    this.router.navigate(['/login']).then(() => {
      window.location.reload();  // Refreshes the app to clear state
    });
  }

  ngOnInit() {
    this.fetchAllPetitions();
    setTimeout(() => {
      const modalElement = document.getElementById('responseModal');
      if (modalElement) {
        this.modal = new bootstrap.Modal(modalElement);
      } else {
        console.error('Modal element not found');
      }
    }, 0);
  }
  
  fetchAllPetitions() {
    this.petitionService.getAllPetitions().subscribe({
      next: (data) => {
        this.petitions = data;
        this.prepareChartData();
        this.prepareWordCloudData();  // Generate word cloud data
      },
      error: (err) => console.error(err)
    });
  }


prepareWordCloudData() {
  const wordFrequency: { [key: string]: number } = {};

  this.petitions.forEach(petition => {
    const words = petition.title.split(' ');  // Using title, can also use petition.text
    words.forEach((word: string) => {
      const cleanedWord = word.toLowerCase().replace(/[^\w\s]/gi, ''); // Remove punctuation
      if (cleanedWord.length > 3) {  // Ignore short words
        wordFrequency[cleanedWord] = (wordFrequency[cleanedWord] || 0) + 1;
      }
    });
  });

  this.wordCloudData = Object.entries(wordFrequency).map(([text, size]) => ({
    text,
    size
  }));
}

  prepareChartData() {
    this.chartLabels = this.petitions.map(pet => pet.title);
    this.chartData = {
      labels: this.chartLabels,
      datasets: [
        {
          data: this.petitions.map(pet => pet.signatures),
          label: 'Signatures',
          backgroundColor: '#6d0fba'
        }
      ]
    };
  }

  getThreshold() {
    this.adminService.getSignatureThreshold().subscribe({
      next: (threshold) => alert('Current threshold is: ' + threshold),
      error: (err) => console.error('Error fetching threshold', err)
    });
  }

  setThreshold() {
    console.log('Updating threshold to:', this.threshold);
  
    this.adminService.setSignatureThreshold(this.threshold).subscribe({
      next: (threshold) => {
        alert('Threshold updated to ' + threshold);  // Updated alert message
      },
      error: (err) => {
        alert('Threshold updated.');
      }
    });
  }
  

  onEvaluateClick(pet: any) {
    if (pet.status === 'closed') { //
      alert('No longer accepting new signatures');
      return; 
    }

    this.adminService.evaluatePetition(pet.id).subscribe({
      next: (thresholdMet) => {
        if (thresholdMet) {
          this.selectedPetitionId = pet.id;
          this.responseInput = '';
          this.modal.show();
        } else {
          alert('Threshold not met yet.');
        }
      },
      error: (err) => alert(err.error?.message || 'Evaluation failed.')
    });
  }

  submitResponse() {
    if (this.selectedPetitionId !== null && this.responseInput.trim() !== '') {
      this.adminService.closePetition(this.selectedPetitionId, this.responseInput).subscribe({
        next: () => {
          alert('Petition closed with your response!');
          this.fetchAllPetitions(); // Refresh data
          this.modal.hide(); 
        },
        error: (err) => alert(err.error?.message || 'Error closing petition.')
      });
    } else {
      alert('Response cannot be empty.');
    }
  }
}
