from django.shortcuts import render
from django.utils import timezone
from django.db.models import Count
from .models import Article, Category


def homepage(request):
    sort = request.GET.get('sort')
    date_from = request.GET.get('date_from')
    date_to = request.GET.get('date_to')

    articles = Article.objects.all()

    # Фільтрація за датою
    if date_from and date_to:
        try:
            start = timezone.datetime.strptime(date_from, '%Y-%m-%d')
            end = timezone.datetime.strptime(date_to, '%Y-%m-%d') + timezone.timedelta(days=1)
            articles = articles.filter(created_at__range=(start, end))
        except ValueError:
            pass

    # Сортування
    if sort == 'comments':
        articles = articles.annotate(comment_count=Count('comments')).order_by('-comment_count')
    elif sort == 'date' or not sort:
        articles = articles.order_by('-created_at')

    return render(request, 'homepage.html', {'articles': articles})

def category_list(request):
    categories = Category.objects.annotate(article_count=Count('articles', distinct=True))

    return render(request, 'category_list.html', {'categories': categories})