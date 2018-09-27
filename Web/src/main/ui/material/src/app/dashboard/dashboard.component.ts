import { Component, OnInit } from '@angular/core';
import { CHARTCONFIG } from '../charts/charts.config';
import { DashboardService } from '../services/dashboard.service';
import { FormGroup, FormBuilder, FormArray } from '@angular/forms'

@Component({
  selector: 'my-dashboard',
  templateUrl: './dashboard.component.html',
  providers: [DashboardService]
})
export class DashboardComponent {
  config = CHARTCONFIG;
  showSpinner: boolean = false;
  projects = "-";
  jobs = "-";
  envs = "-"
  runs = "-"
  tests = "-";
  time = "-";
  bytes = "-";
  iBots = "-";
  iTracker="-";
  eBots = "-";
  suites = "-";
  channels = "-";
  datePicker = { startDate:"", endDate: "" }

  datePickerForm: FormGroup;

  constructor(private dashboardService: DashboardService, private formBuilder: FormBuilder) {
    this.datePickerForm = this.formBuilder.group({
      startDate: [this.datePicker.startDate],
      endDate: [this.datePicker.endDate]
    });  

  }
  ngOnInit() {
      this.get("count-projects", "projects");
      this.get("count-jobs", "jobs");
      this.get("count-envs", "envs");
      this.get("count-suites", "suites");
      this.get("count-runs", "runs");
      this.get("count-tests", "tests");
      this.get("count-time", "time");
      this.get("count-bytes", "bytes");
      this.get("count-ibots", "iBots");
      this.get("count-bugs", "bugs");

      this.get("count-ebots", "eBots");
      this.get("count-channels", "channels");
  }
  
  get(stat: string, _var: string) {
    this.dashboardService.getStat(stat).subscribe(results => {
      if (results['errors']) {
        // TODO - handle errors
        return;
      }
      var count = results['data'];
      if (_var === 'projects')
        this.projects = count;
      else if (_var === 'jobs')
        this.jobs = count;
      else if (_var === 'suites')
        this.suites = count;
      else if (_var === 'envs')
        this.envs = count;
      else if (_var === 'runs')
        this.runs = count;
      else if (_var === 'tests')
        this.tests = count;
      else if (_var === 'time')
        this.time = count;
      else if (_var === 'bytes')
        this.bytes = count, 1;
      else if (_var === 'iBots')
        this.iBots = count;
        else if (_var === 'bugs')
        this.iTracker = count;
      else if (_var === 'eBots')
        this.eBots = count;
      else if (_var === 'channels')
        this.channels = count;
    }, error => {
      console.log("Unable to fetch stat");
    });
  }

  getMonData = () => {
    const data = [];
    for (let i = 0; i < 13; i++) {
      data.push('Mon. ' + i);
    }
    return data;
  }

  monData = this.getMonData();

  //
  trafficChart = {
    legend: {
      show: true,
      x: 'right',
      y: 'top',
      textStyle: {
        color: this.config.textColor
      },
      data: ['Trend', 'Search', 'Paid Ads', 'Virality']
    },
    grid: {
      x: 40,
      y: 60,
      x2: 40,
      y2: 30,
      borderWidth: 0
    },
    tooltip: {
      show: true,
      trigger: 'axis',
      axisPointer: {
        lineStyle: {
          color: this.config.gray
        }
      }
    },
    xAxis: [
      {
        type : 'category',
        axisLine: {
          show: false
        },
        axisTick: {
          show: false
        },
        axisLabel: {
          textStyle: {
            color: this.config.textColor
          }
        },
        splitLine: {
          show: false,
          lineStyle: {
            color: this.config.splitLineColor
          }
        },
        data : this.monData
      }
    ],
    yAxis: [
      {
        type : 'value',
        axisLine: {
          show: false
        },
        axisTick: {
          show: false
        },
        axisLabel: {
          textStyle: {
            color: this.config.textColor
          }
        },
        splitLine: {
          show: true,
          lineStyle: {
            color: this.config.splitLineColor
          }
        }
      }
    ],
    series: [
      {
        name: 'Trend',
        type: 'line',
        clickable: false,
        lineStyle: {
          normal: {
            color: this.config.gray,
          }
        },
        areaStyle: {
          normal: {
            color: this.config.gray,
          },
          emphasis: {}
        },
        data: [41, 85, 27, 70, 50, 57, 41, 56, 69, 52, 48, 44, 35],
        legendHoverLink: false,
        z: 1
      },
      {
        name: 'Search',
        type: 'bar',
        stack: 'traffic',
        clickable: false,
        itemStyle: {
          normal: {
            color: this.config.success, // '#8BC34A', // Light Green 500
            barBorderRadius: 0
          },
          emphasis: {
            // color: this.config.success
          }
        },
        barCategoryGap: '60%',
        data: [25, 45, 15, 39, 20, 26, 23, 26, 35, 27, 26, 25, 22],
        legendHoverLink: false,
        z: 2
      },
      {
        name: 'Paid Ads',
        type: 'bar',
        stack: 'traffic',
        smooth: true,
        itemStyle: {
          normal: {
            color: this.config.primary, // '#03A9F4', // Light Blue 500
            barBorderRadius: 0
          },
          emphasis: {
            // color: this.config.primary
          }
        },
        barCategoryGap: '60%',
        data: [10, 25, 6, 19, 24, 25, 12, 15, 26, 13, 12, 8, 7],
        symbol: 'none',
        legendHoverLink: false,
        z: 2
      },
      {
        name: 'Virality',
        type: 'bar',
        stack: 'traffic',
        smooth: true,
        itemStyle: {
          normal: {
            color: this.config.info, // '#4FC3F7', // Light Blue 300
            barBorderRadius: 0
          },
          emphasis: {
            // color: this.config.info
          }
        },
        barCategoryGap: '60%',
        data: [6, 15, 6, 12, 6, 6, 6, 15, 8, 13, 10, 11, 6],
        symbol: 'none',
        legendHoverLink: false,
        z: 2
      }
    ]
  };

  //
  donutChart = {
    tooltip : {
      trigger: 'item',
      formatter: '{a} <br/>{b} : {c} ({d}%)'
    },
    legend: {
      show : false,
      orient : 'vertical',
      x : 'right',
      data: ['Direct', 'Email', 'Affiliate', 'Video Ads', 'Search']
    },
    toolbox: {
      show : false,
      feature : {
        restore : {show: true, title: 'restore'},
        saveAsImage : {show: true, title: 'save as image'}
      }
    },
    calculable : true,
    series : [
      {
        name: 'Traffic source',
        type: 'pie',
        radius : ['51%', '69%'],
        itemStyle : {
          normal : {
            color: this.config.info
          },
          emphasis : {
            label : {
              show : true,
              position : 'center',
              textStyle : {
                fontSize : '20',
                fontWeight : 'normal'
              }
            }
          }
        },
        data: [
          {
            value: 40,
            name: 'United States',
            itemStyle: {
              normal: {
                color: this.config.success,
                label: {
                  textStyle: {
                    color: this.config.success
                  }
                },
                labelLine: {
                  lineStyle: {
                    color: this.config.success
                  }
                }
              }
            }
          },
          {
            value: 10,
            name: 'United Kingdom',
            itemStyle: {
              normal: {
                color: this.config.primary,
                label: {
                  textStyle: {
                    color: this.config.primary
                  }
                },
                labelLine: {
                  lineStyle: {
                    color: this.config.primary
                  }
                }
              }
            }
          },
          {
            value: 20,
            name: 'Canada',
            itemStyle: {
              normal: {
                color: this.config.infoAlt,
                label: {
                  textStyle: {
                    color: this.config.infoAlt
                  }
                },
                labelLine: {
                  lineStyle: {
                    color: this.config.infoAlt
                  }
                }
              }
            }
          },
          {
            value: 12,
            name: 'Germany',
            itemStyle: {
              normal: {
                color: this.config.info,
                label: {
                  textStyle: {
                    color: this.config.info
                  }
                },
                labelLine: {
                  lineStyle: {
                    color: this.config.info
                  }
                }
              }
            }
          },
          {
            value: 18,
            name: 'Japan',
            itemStyle: {
              normal: {
                color: this.config.warning,
                label: {
                  textStyle: {
                    color: this.config.warning
                  }
                },
                labelLine: {
                  lineStyle: {
                    color: this.config.warning
                  }
                }
              }
            }
          }
        ]
      }
    ]
  };

  //
  radarChart = {
    tooltip : {},
    legend: {
      show: false, // because the legend symbol color is not the same with radar line
      orient : 'vertical',
      x : 'right',
      y : 'bottom',
      data: ['Budget', 'Spending']
    },
    toolbox: {
      show : false
    },
    radar : [
       {
        axisLine: {
          show: true,
          lineStyle: {
            // for both indicator and axisLine, bad, better seperate them
            color: '#b1b1b1'
          }
        },
        splitLine: {
          lineStyle: {
            color: 'rgba(0,0,0,.1)'
          }
        },
        splitArea: {
          show: true,
          areaStyle: {
            color: this.config.splitAreaColor
          }
        },
        indicator : [
          { name: 'sales', max: 6000},
          { name: 'dministration', max: 16000},
          { name: 'Information Techology', max: 30000},
          { name: 'Customer Support', max: 38000},
          { name: 'Development', max: 52000},
          { name: 'Marketing', max: 25000}
        ]
      }
    ],
    calculable : true,
    series : [
      {
        type: 'radar',
        data : [
          {
            name : 'Budget',
            value : [4300, 10000, 28000, 35000, 50000, 19000],
            itemStyle: {
              normal: {
                color: this.config.primary
              }
            }
          },
          {
            name : 'Spending',
            value : [5000, 14000, 28000, 31000, 42000, 21000],
            itemStyle: {
              normal: {
                color: this.config.success
              }
            }
          }
        ]
      }
    ]
  };
}
