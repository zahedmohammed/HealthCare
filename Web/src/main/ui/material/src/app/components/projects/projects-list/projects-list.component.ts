import { CHARTCONFIG } from './../../../charts/charts.config';
import { DashboardService } from './../../../services/dashboard.service';
import { Component, OnInit, ViewChild } from '@angular/core';
import { ProjectService } from '../../../services/project.service';
import { Handler } from '../../dialogs/handler/handler';
import { Chart } from 'chart.js';

@Component({
  selector: 'app-projects-list',
  templateUrl: './projects-list.component.html',
  styleUrls: ['./projects-list.component.scss'],
  providers: [ProjectService, DashboardService]
})
export class ProjectsListComponent implements OnInit {
  runs = "-";
  runsChartTile = []; // This will hold our tile chart info
  config = CHARTCONFIG;
  projects;
  autocodeConfig;
  projectTitle: string = "Projects";
  showSpinner: boolean = false;
  keyword: string = '';
  constructor(private projectService: ProjectService, private dashboardService: DashboardService, private handler: Handler) { }

  ngOnInit() {
    this.list();
    this.get("count-runs", "runs");
  }
  ngAfterViewInit() {
    //this.getChartOnTile();
  }
  get(stat: string, _var: string) {
    this.dashboardService.getStat(stat).subscribe(results => {
      if (results['errors']) {
        // TODO - handle errors
        return;
      }
      let count = results['data'];
      this.runs = count;
      let maxRuns = 30000;
      let currentRuns: any = this.runs;
      //let currentRuns: any = 6000;
      if(maxRuns == currentRuns){
        currentRuns = 0;
      }
      
      //Text inside the doughnut
      // Chart.pluginService.register({
      //   beforeDraw: function(chart) {
      //     var width = chart.chart.width,
      //         height = chart.chart.height,
      //         ctx = chart.chart.ctx;
      
      //     ctx.restore();
      //     var fontSize = (height / 100).toFixed(2);
      //     ctx.font = fontSize + "em sans-serif";
      //     ctx.textBaseline = "middle";
      
      //     var text = currentRuns,
      //         textX = Math.round((width - ctx.measureText(text).width) / 2),
      //         textY = height / 2;
      
      //     ctx.fillText(text, textX, textY);
      //     ctx.save();
      //   }
      // });

      //Chart Tile start
      this.runsChartTile = new Chart('canvas1', {
        type: 'doughnut',
        data: {
          labels: ["Total Runs", "Remaining Runs"],
          datasets: [
            {
              data: [currentRuns, maxRuns - currentRuns],
              backgroundColor: this.config.redBlue
            }
          ]
        },
        options: {
          responsive: true,
          cutoutPercentage: 65,
          legend: {
            display: true,
            position: 'bottom'
          },
          title: {
            text: 'Runs',
            display: false
          },
          animation: {
            duration: 1000,
            animateScale: true,
            easing: 'linear'
          },
          plugins: {
            datalabels: {
              backgroundColor: function(context) {
                return context.dataset.backgroundColor;
              },
              display: function(context) {
                var dataset = context.dataset;            
                var value = dataset.data[context.dataIndex];
                return value > 0;
              },
              color: 'white'
            }
          }
        }
      });
    }, error => {
      console.log("Unable to fetch stat Tiles");
    });
  }

  // getChartOnTile() {
  //   //Graph 1 Start
  //   let runsTemp = this.runs;
  //   console.log("runsTemp", runsTemp)
  //   let maxRuns = 30000;
  //   let currentRuns = 3000;
  //   if(maxRuns == currentRuns){
  //     console.log(maxRuns)
  //     console.log(currentRuns)
  //     currentRuns = 0;
  //   }
  //   // value = maxRuns;
  //   // console.log(value);
  //   // //let currentRuns = [65, 1000];
  //   // let currentRuns = this.runs;
  //   // console.log(currentRuns)
  //   // this.runsChartTile = new Chart('canvas1', {
  //   //   type: 'doughnut',
  //   //   data: {
  //   //     labels: ["Current Runs", "Remaining Runs"],
  //   //     datasets: [
  //   //       {
  //   //         data: [currentRuns, maxRuns - currentRuns],
  //   //         backgroundColor: this.config.redBlue
  //   //       },
  //   //       // {
  //   //       //   data: [222, 348],
  //   //       //   backgroundColor: this.config.multiColors
  //   //       // },
  //   //       // {
  //   //       //   data: [23, 46],
  //   //       //   backgroundColor: this.config.backgroundColor
  //   //       // }
  //   //     ]
  //   //   },
  //   //   options: {
  //   //     responsive: true,
  //   //     cutoutPercentage: 65,
  //   //     legend: {
  //   //       display: false,
  //   //       position: 'left'
  //   //     },
  //   //     title: {
  //   //       text: 'Runs',
  //   //       display: false
  //   //     },
  //   //     animation: {
  //   //       duration: 1000,
  //   //       animateScale: true,
  //   //       easing: 'linear'
  //   //     },
  //   //     plugins: {
  //   //       labels: [
  //   //         {
  //   //           fontSize: 9,
  //   //           fontStyle: 'normal',
  //   //           fontColor: "white",
  //   //           textShadow: true,
  //   //           overlap: false,
  //   //           arc: true,
              
  //   //         }
  //   //       ]
  //   //     }
  //   //   }
  //   // });
  // }
  list() {
    this.handler.activateLoader();
    this.projectService.getProjects(this.page, this.pageSize).subscribe(results => {
      this.handler.hideLoader();
      if (this.handler.handle(results)) {
        return;
      }
      this.projects = results['data'];
      console.log(this.projects)
      this.length = results['totalElements'];
    }, error => {
      this.handler.hideLoader();
      this.handler.error(error);
    });
  }

  //  Search project By Name

  searchProject() {
    if (this.keyword.length > 1) {
      this.handler.activateLoader();
      this.projectService.searchProject(this.keyword, this.page, this.pageSize).subscribe(results => {
        this.handler.hideLoader();
        if (this.handler.handle(results)) {
          return;
        }
        this.projects = results['data'];
        this.length = results['totalElements'];
      }, error => {
        this.handler.hideLoader();
        this.handler.error(error);
      });
    }
    if (this.keyword.length <= 1) {
      this.list();
    }
  }

  length = 0;
  page = 0;
  pageSize = 7;
  change(evt) {
    this.page = evt['pageIndex'];
    this.list();
  }

}
