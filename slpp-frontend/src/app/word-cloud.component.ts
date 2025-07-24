import { Component, ElementRef, Input, OnInit, ViewChild } from '@angular/core';
import * as d3 from 'd3';
import cloud from 'd3-cloud';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-word-cloud',
  standalone: true,
  imports: [CommonModule],
  template: `<div #wordCloud></div>`,
  styles: [`div { width: 100%; height: 400px; }`]
})
export class WordCloudComponent implements OnInit {
  @Input() words: { text: string; size: number }[] = [];
  @ViewChild('wordCloud', { static: true }) wordCloud!: ElementRef;

  ngOnInit(): void {
    this.drawWordCloud();
    window.addEventListener('resize', this.onResize.bind(this));
  }
  
  onResize() {
    this.drawWordCloud();
  }
  
  drawWordCloud() {
    const width = this.wordCloud.nativeElement.offsetWidth;
    const height = this.wordCloud.nativeElement.offsetHeight;
  
    cloud()
      .size([width, height])
      .words(this.words.map(d => ({ text: d.text, size: d.size })))
      .padding(5)
      .rotate(() => ~~(Math.random() * 2) * 90)
      .font('Impact')
      .fontSize((d: any) => (d.size || 10) * (width / 500) )
      .on('end', (words: any[]) => {
        d3.select(this.wordCloud.nativeElement)
          .html('')  // Clear previous content on resize
          .append('svg')
          .attr('width', width)
          .attr('height', height)
          .append('g')
          .attr('transform', `translate(${width / 2}, ${height / 2})`)
          .selectAll('text')
          .data(words)
          .enter().append('text')
          .style('font-size', (d: any) => `${d.size}px`)
          .style('font-family', 'Impact')
          .style('fill', () => d3.schemeCategory10[Math.floor(Math.random() * 10)])
          .attr('text-anchor', 'middle')
          .attr('transform', (d: any) => `translate(${d.x}, ${d.y}) rotate(${d.rotate})`)
          .text((d: any) => d.text);
      })
      .start();
  }
}
