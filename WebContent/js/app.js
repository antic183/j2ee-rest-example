(function($) {
  $(document).ready(function() {
    $(document).on('submit', '#myForm', function(e) {
      e.preventDefault();

      var formData = $(this).serializeJSON({parseBooleans: true}),
          data = formData.dataType !== 'Json'
                 ? {firstName: formData.firstName, lastName: formData.lastName}
                 : JSON.stringify({firstName: formData.firstName, lastName: formData.lastName});

      $.ajax({
        url : $(this).attr('action') + formData.pathParam,
        dataType : formData.dataType,
        cache : false,
        contentType : formData.contentType,
        processData : formData.processData,
        data : data,
        type : formData.requestMethode,
        success : function(res) {
          console.info(res);
        },
        fail : function(err) {
          console.warn(err);
        }
      });
    });

    $(document).foundation();
  });
})(jQuery);
