/**
 *
 */

$('document').ready(function () {

    $('#comboBoxVille').change(
        function () {
            var villeId = $(this).val();
           $.ajax({
               type:'GET',
               url:'/loadCinema/'+villeId,
               dataType:"json",
               contentType:"application/json",
               cache:false,
               success: function (result) {
                   var s='';
                   for(var i=0;i<result.length;i++){
                       s+='<option th:value="'+result[i].id + '" th:text="' +result[i].name + '"></option>';

                   }
                   alert(villeId)
                   $('#comboBoxCinema').html(s);

               }
           })
        });
});
