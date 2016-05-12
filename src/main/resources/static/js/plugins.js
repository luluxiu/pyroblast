$(function() {

    var formElements = function(){
        // Bootstrap datepicker
        var feDatepicker = function(){
            if($(".datepicker").length > 0){
                $(".datepicker").datepicker({format: 'yyyy-mm-dd'});
                $("#dp-2,#dp-3,#dp-4").datepicker(); // Sample
            }

        }// END Bootstrap datepicker

        //Bootstrap timepicker
        var feTimepicker = function(){
            // Default timepicker
            if($(".timepicker").length > 0)
                $('.timepicker').timepicker();

            // 24 hours mode timepicker
            if($(".timepicker24").length > 0)
                $(".timepicker24").timepicker({minuteStep: 5,showSeconds: true,showMeridian: false});

        }// END Bootstrap timepicker

        //Daterangepicker
        var feDaterangepicker = function(){
            if($(".daterange").length > 0)
               $(".daterange").daterangepicker({format: 'YYYY-MM-DD',startDate: '2013-01-01',endDate: '2013-12-31'});
        }
        // END Daterangepicker

        //Bootstrap colopicker
        var feColorpicker = function(){
            // Default colorpicker hex
            if($(".colorpicker").length > 0)
                $(".colorpicker").colorpicker({format: 'hex'});

            // RGBA mode
            if($(".colorpicker_rgba").length > 0)
                $(".colorpicker_rgba").colorpicker({format: 'rgba'});

            // Sample
            if($("#colorpicker").length > 0)
                $("#colorpicker").colorpicker();

        }// END Bootstrap colorpicker

        //Bootstrap select
        var feSelect = function(){
            if($(".select").length > 0){
                $(".select").selectpicker();

                $(".select").on("change", function(){
                    if($(this).val() == "" || null === $(this).val()){
                        if(!$(this).attr("multiple"))
                            $(this).val("").find("option").removeAttr("selected").prop("selected",false);
                    }else{
                        $(this).find("option[value="+$(this).val()+"]").attr("selected",true);
                    }
                });
            }
        }//END Bootstrap select


        //Validation Engine
        var feValidation = function(){
            if($("form[id^='validate']").length > 0){

                // Validation prefix for custom form elements
                var prefix = "valPref_";

                //Add prefix to Bootstrap select plugin
                $("form[id^='validate'] .select").each(function(){
                   $(this).next("div.bootstrap-select").attr("id", prefix + $(this).attr("id")).removeClass("validate[required]");
                });

                // Validation Engine init
                $("form[id^='validate']").validationEngine('attach', {promptPosition : "bottomLeft", scroll: false,
                                                                        onValidationComplete: function(form, status){
                                                                            form.validationEngine("updatePromptsPosition");
                                                                        },
                                                                        prettySelect : true,
                                                                        usePrefix: prefix
                                                                     });
            }
        }//END Validation Engine

        //Masked Inputs
        var feMasked = function(){
            if($("input[class^='mask_']").length > 0){
                $("input.mask_tin").mask('99-9999999');
                $("input.mask_ssn").mask('999-99-9999');
                $("input.mask_date").mask('9999-99-99');
                $("input.mask_product").mask('a*-999-a999');
                $("input.mask_phone").mask('99 (999) 999-99-99');
                $("input.mask_phone_ext").mask('99 (999) 999-9999? x99999');
                $("input.mask_credit").mask('9999-9999-9999-9999');
                $("input.mask_percent").mask('99%');
            }
        }//END Masked Inputs

        //Bootstrap tooltip
        var feTooltips = function(){
            $("body").tooltip({selector:'[data-toggle="tooltip"]',container:"body"});
        }//END Bootstrap tooltip

        //Bootstrap Popover
        var fePopover = function(){
            $("[data-toggle=popover]").popover();
            $(".popover-dismiss").popover({trigger: 'focus'});
        }//END Bootstrap Popover

        //Tagsinput
        var feTagsinput = function(){
            if($(".tagsinput").length > 0){

                $(".tagsinput").each(function(){

                    if($(this).data("placeholder") != ''){
                        var dt = $(this).data("placeholder");
                    }else
                        var dt = 'add a tag';

                    $(this).tagsInput({width: '100%',height:'auto',defaultText: dt});
                });

            }
        }// END Tagsinput

        //iCheckbox and iRadion - custom elements
        var feiCheckbox = function(){
            if($(".icheckbox").length > 0){
                 $(".icheckbox,.iradio").iCheck({checkboxClass: 'icheckbox_minimal-grey',radioClass: 'iradio_minimal-grey'});
            }
        }
        // END iCheckbox

        //Bootstrap file input
        var feBsFileInput = function(){

            if($("input.fileinput").length > 0)
                $("input.fileinput").bootstrapFileInput();

        }
        //END Bootstrap file input

        return {// Init all form element features
		init: function(){
                    feDatepicker();
                    feTimepicker();
                    feColorpicker();
                    feSelect();
                    feValidation();
                    feMasked();
                    feTooltips();
                    fePopover();
                    feTagsinput();
                    feiCheckbox();
                    feBsFileInput();
                    feDaterangepicker();
                }
        }
    }();


    formElements.init();


    /* My Custom Progressbar */
    $.mpb = function(action,options){

        var settings = $.extend({
            state: '',
            value: [0,0],
            position: '',
            speed: 20,
            complete: null
        },options);

        if(action == 'show' || action == 'update'){

            if(action == 'show'){
                $(".mpb").remove();
                var mpb = '<div class="mpb '+settings.position+'">\n\
                               <div class="mpb-progress'+(settings.state != '' ? ' mpb-'+settings.state: '')+'" style="width:'+settings.value[0]+'%;"></div>\n\
                           </div>';
                $('body').append(mpb);
            }

            var i  = $.isArray(settings.value) ? settings.value[0] : $(".mpb .mpb-progress").width();
            var to = $.isArray(settings.value) ? settings.value[1] : settings.value;

            var timer = setInterval(function(){
                $(".mpb .mpb-progress").css('width',i+'%'); i++;

                if(i > to){
                    clearInterval(timer);
                    if($.isFunction(settings.complete)){
                        settings.complete.call(this);
                    }
                }
            }, settings.speed);

        }

        if(action == 'destroy'){
            $(".mpb").remove();
        }

    }
    /* Eof My Custom Progressbar */


    // New selector case insensivity
     $.expr[':'].containsi = function(a, i, m) {
         return jQuery(a).text().toUpperCase().indexOf(m[3].toUpperCase()) >= 0;
     };
});

Object.size = function(obj) {
    var size = 0, key;
    for (key in obj) {
        if (obj.hasOwnProperty(key)) size++;
    }
    return size;
};