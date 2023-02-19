

if(localStorage.getItem('difficulty')!=null){
window.location.href = localStorage.getItem('difficulty');}

$('#easyBtn').click(e => {
    localStorage.setItem('difficulty','web/game.html?difficulty=0')
    window.location.href = 'web/game.html?difficulty=0'
});
$('#hardBtn').click(e => {
    localStorage.setItem('difficulty','web/game.html?difficulty=1')
    window.location.href = 'web/game.html?difficulty=1'
})
let deferredPrompt;
window.addEventListener('beforeinstallprompt', (e) => {
    deferredPrompt = e;
});

const installApp = document.getElementById('installAppBtn');
installApp.addEventListener('click', async () => {
    if (deferredPrompt !== null) {
        deferredPrompt.prompt();
        const { outcome } = await deferredPrompt.userChoice;
        if (outcome === 'accepted') {
            deferredPrompt = null;
        }
    }
});
function ToggleInfo() {
    $(".modal").modal('toggle')
};
var fontSize = parseInt($("#column").width()-10) * 0.17;  
$().ready(() => {      
    $('#name').css('font-size', fontSize);
    $('#instructionsBtn').click(()=>{
        ToggleInfo();
    });  
    document.querySelectorAll('.disableSelect').forEach(l =>
        l.onselectstart = function () { return false }
      );
    $('#printInstructionsBtn').click(()=>{
        window.print();
    })
});
$(window).resize(()=>{
    fontSize = parseInt($("#column").width()) * 0.17;
    $('#name').css('font-size', fontSize);
})

function RemoveDiff(){
    localStorage.removeItem('difficulty');
  }
