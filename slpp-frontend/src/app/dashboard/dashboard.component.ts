import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { PetitionService } from '../services/petition.service';
import { SignatureService } from '../services/signature.service';
import { AuthService } from '../services/auth.service';
import { Router } from '@angular/router';

@Component({
    selector: 'app-dashboard',
    standalone:true,
    imports: [CommonModule, FormsModule], // Include FormsModule for ngModel
    templateUrl: './dashboard.component.html'
})
export class DashboardComponent implements OnInit {

  petitions: any[] = [];

  // For create petition form
  showCreateForm = false;
  newPetition = {
    title: '',
    text: ''
  };

  // For "View" modal
  showModal = false;
  selectedPetition: any | null = null;

  constructor(
    private router:Router, 
    private petitionService: PetitionService,
    private signatureService: SignatureService,
    private authService: AuthService
  ) {}

  logout(): void {
    this.authService.clearToken();
    this.router.navigate(['/login']);
  }
  ngOnInit() {
    this.fetchPetitions();
  }

  //PETITIONS
  fetchPetitions(): void {
    this.petitionService.getAllPetitions().subscribe({
      next: (data) => {
        this.petitions = data;
      },
      error: (err) => {
        console.error('Error fetching petitions:', err);
      }
    });
  }

  // Toggle the create form on or off
  toggleCreateForm() {
    this.showCreateForm = !this.showCreateForm;
  }

  // Submits the new petition data to the server
  submitNewPetition() {
    const payload = {
      title: this.newPetition.title,
      text: this.newPetition.text
    };

    this.petitionService.simpleCreatePetition(payload).subscribe({
      next: (res) => {
        alert('Petition created.');
        // Hide form+ reset fields
        this.showCreateForm = false;
        this.newPetition = { title: '', text: '' };
        // Refresh
        this.fetchPetitions();
      },
      error: (err) => {
        console.error('Create petition error', err);
        alert('Failed to create petition');
      }
    });
  }

  // SIGNATURES
  signPetition(petitionId: number): void {
    const userId = this.authService.userId;
    if (!userId) {
      alert('No user ID found. Are you logged in?');
      return;
    }
  
    this.signatureService.signPetition(petitionId, userId).subscribe({
      next: () => {
        alert('Signed successfully!');
        this.fetchPetitions();
      },
      error: (err) => {
        console.error('Sign error', err);
  
        // Safely handle non-JSON error responses
        let errorMessage = 'Only 1 signature per petition per petitioner allowed.';
  
        if (err.error && typeof err.error === 'object') {
          errorMessage = errorMessage || err.error.message;
        } else if (typeof err.error === 'string') {
          try {
            const parsedError = JSON.parse(err.error);
            errorMessage = errorMessage || parsedError.message;
          } catch {
            errorMessage = err.error; // fallback
          }
        }
        alert(errorMessage);
      }
    });
  }

  //  VIEW PETITION DETAILS
  viewPetition(petitionId: number) {
    this.petitionService.getPetitionById(petitionId).subscribe({
      next: (petition) => {
        this.selectedPetition = petition;
        this.showModal = true;
      },
      error: (err) => {
        console.error('View error', err);}
    });
  }

  closeModal() {
    this.showModal = false;
    this.selectedPetition = null;
  }
}
